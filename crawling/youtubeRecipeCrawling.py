from dotenv import load_dotenv
import os
import requests
import re
from youtube_transcript_api import YouTubeTranscriptApi
from urllib.parse import urlparse, parse_qs
import yt_dlp
import json

load_dotenv()

# OpenAI API 키 설정
api_key = os.getenv('OPENAI_API_KEY')

def extract_video_id(url):
    parsed_url = urlparse(url)
    if parsed_url.netloc == 'www.youtube.com':
        query_params = parse_qs(parsed_url.query)
        return query_params.get('v', [None])[0]
    elif parsed_url.netloc == 'youtu.be':
        return parsed_url.path[1:]
    return None

def format_time(seconds):
    minutes = int(seconds // 60)
    seconds = int(seconds % 60)
    return f"{minutes:02}:{seconds:02}"

def get_transcript(video_url, lang_code='ko'):
    """
    YouTube URL에서 자막을 추출하고, 한국어 자막이 없으면 영어 자막을 가져옵니다.
    """
    video_id = extract_video_id(video_url)
    if not video_id:
        print("유효한 YouTube URL을 입력하세요.")
        return None, None  # 자막과 언어를 None으로 반환

    used_lang = lang_code  # 기본 언어는 'ko'

    try:
        # 한국어 자막 시도
        transcript = YouTubeTranscriptApi.get_transcript(video_id, languages=[lang_code])
        print("한국어 자막을 성공적으로 가져왔습니다.")
    except:
        try:
            # 영어 자막 시도
            print("한국어 자막이 없어서 영어 자막을 가져옵니다.")
            transcript = YouTubeTranscriptApi.get_transcript(video_id, languages=['en'])
            used_lang = 'en'  # 사용된 언어를 'en'으로 설정
        except Exception as e:
            print(f"자막을 가져오는 데 문제가 발생했습니다: {e}")
            return None, None  # 자막과 언어를 None으로 반환

    # 자막을 JSON 형식으로 반환합니다.
    transcript_json = [{"start": format_time(entry['start']),
                        "end": format_time(entry['start'] + entry['duration']),
                        "text": entry['text']} for entry in transcript]
    return transcript_json, used_lang  # 자막과 사용된 언어를 반환

def translate_to_korean(texts):
    """
    영어 텍스트 리스트를 한글로 번역합니다.
    """
    combined_text = "\n".join(texts)

    messages = [
        {"role": "system", "content": "You are a helpful assistant that translates English to Korean."},
        {"role": "user", "content": f"Translate the following English text to Korean:\n{combined_text}"}
    ]

    headers = {
        'Authorization': f'Bearer {api_key}',
        'Content-Type': 'application/json',
    }

    data = {
        'model': 'gpt-3.5-turbo',
        'messages': messages,
        'max_tokens': 2000,
        'temperature': 0.7
    }

    response = requests.post('https://api.openai.com/v1/chat/completions', headers=headers, json=data)

    if response.status_code == 200:
        translation = response.json()['choices'][0]['message']['content'].strip()

        # 번역된 텍스트를 원래 리스트의 크기로 분할
        translated_texts = translation.split('\n')
        original_line_count = len(texts)

        if len(translated_texts) != original_line_count:
            print("번역된 텍스트의 줄 수가 원본과 일치하지 않음: 원본 자막 길이에 맞추어 조정 중입니다.")

            # 번역된 텍스트가 원본보다 긴 경우: 남은 부분을 마지막 자막에 추가
            if len(translated_texts) > original_line_count:
                adjusted_texts = translated_texts[:original_line_count - 1] + [' '.join(translated_texts[original_line_count - 1:])]
            # 번역된 텍스트가 원본보다 짧은 경우: 부족한 부분을 빈 문자열로 채움
            else:
                adjusted_texts = translated_texts + [''] * (original_line_count - len(translated_texts))

            return adjusted_texts

        return translated_texts
    else:
        raise Exception(f"OpenAI API 요청 실패: {response.status_code} - {response.text}")

def extract_description(youtube_url):
    ydl_opts = {
        'quiet': True,
        'skip_download': True,
        'writeinfojson': False,
        'extract_flat': True,
        'force_generic_extractor': True
    }

    with yt_dlp.YoutubeDL(ydl_opts) as ydl:
        try:
            info_dict = ydl.extract_info(youtube_url, download=False)
            description = info_dict.get('description', '')
            return description
        except Exception as e:
            print(f"설명을 가져오는 데 문제가 발생했습니다: {e}")
            return None

def extract_title(youtube_url):
    ydl_opts = {
        'quiet': True,
        'skip_download': True,
        'writeinfojson': False,
        'extract_flat': True,
        'force_generic_extractor': True
    }

    with yt_dlp.YoutubeDL(ydl_opts) as ydl:
        try:
            info_dict = ydl.extract_info(youtube_url, download=False)
            title = info_dict.get('title', '제목을 가져오는 데 실패했습니다.')
            return title
        except Exception as e:
            print(f"제목을 가져오는 데 문제가 발생했습니다: {e}")
            return "제목을 가져오는 데 실패했습니다."

def extract_recipe_details(json_data):
    recipe_name = json_data.get('recipe_name', 'Unknown')
    url = json_data.get('url', 'Unknown')
    subtitles = json_data.get('subtitles', [])
    return recipe_name, url, subtitles

def summarize_recipe(recipe_name, url, subtitles):
    subtitles_text = '\n'.join([f"{i+1}단계({subtitle['start']}-{subtitle['end']}) : {subtitle['text']}" for i, subtitle in enumerate(subtitles)])

    messages = [
        {"role": "system", "content": "당신은 요리 레시피를 요약하는 도우미입니다."},
        {"role": "user", "content": (f"레시피명: {recipe_name}\n"
                                     f"URL: {url}\n"
                                     f"자막:\n{subtitles_text}\n"
                                     "위 정보를 바탕으로 요리 레시피를 요약해 주세요. 형식은 다음과 같습니다:\n"
                                     "레시피명 : \n"
                                     "URL : \n"
                                     "요리 소개 : \n"
                                     "재료 : \n"
                                     "요리 단계\n"
                                     "1단계(타임스탬프) : \n"
                                     "2단계(타임스탬프) : \n"
                                     "n단계(타임스탬프) : \n")}
    ]

    headers = {
        'Authorization': f'Bearer {api_key}',
        'Content-Type': 'application/json',
    }

    data = {
        'model': 'gpt-3.5-turbo',
        'messages': messages,
        'max_tokens': 1500,
        'temperature': 0.7
    }

    response = requests.post('https://api.openai.com/v1/chat/completions', headers=headers, json=data)

    if response.status_code == 200:
        summary = response.json()['choices'][0]['message']['content'].strip()
        return summary
    else:
        raise Exception(f"OpenAI API 요청 실패: {response.status_code} - {response.text}")

def main():
    youtube_url = input("YouTube 동영상 URL을 입력하세요: ").strip()
    title = extract_title(youtube_url)
    transcript_json, used_lang = get_transcript(youtube_url, lang_code='ko')
    description = extract_description(youtube_url)

    if not transcript_json:
        print("자막이 제공되지 않는 동영상이거나 지원되지 않는 언어입니다.")
        return

    youtube_data = {
        'recipe_name': title,
        'url': youtube_url,
        'description': description,
        'subtitles': transcript_json
    }

    if used_lang == 'en':
        english_texts = [subtitle['text'] for subtitle in youtube_data['subtitles']]
        translated_texts = translate_to_korean(english_texts)

        for i, subtitle in enumerate(youtube_data['subtitles']):
            subtitle['text'] = translated_texts[i]

    with open('youtube_data.json', 'w', encoding='utf-8') as file:
        json.dump(youtube_data, file, ensure_ascii=False, indent=4)

    with open('youtube_data.json', 'r', encoding='utf-8') as file:
        json_data = json.load(file)

    recipe_name, url, subtitles = extract_recipe_details(json_data)
    try:
        summary = summarize_recipe(recipe_name, url, subtitles)
        with open('recipe_summary.txt', 'w', encoding='utf-8') as file:
            file.write("==레시피 요약==\n")
            file.write(summary)
        print("레시피 요약이 'recipe_summary.txt' 파일에 저장되었습니다.")
    except Exception as e:
        print(f"에러 발생: {e}")

if __name__ == "__main__":
    main()