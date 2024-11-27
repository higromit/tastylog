import requests
from bs4 import BeautifulSoup
import json
import re  # 정규식을 사용하기 위한 모듈

# 레시피 데이터를 저장할 리스트 및 딕셔너리 초기화
recipe = []
recipe_list = {}

# JSON 파일로 저장하는 함수
def toJson():
    with open('recipe.json', 'w', encoding='utf-8') as file:
        json.dump(recipe_list, file, ensure_ascii=False, indent='\t')

# 페이지 크롤링 함수
def PageCrawler(start_num, end_num):
    for num in range(start_num, end_num + 1):  # end_num을 포함하도록 범위 조정
        url = baseUrl + str(num)

        page = requests.get(url)
        soup = BeautifulSoup(page.content, 'html.parser')

        recipe_title = ''   # 레시피 제목
        ingredients_main = []  # 메인 재료 리스트 (DB 저장용)
        ingredients_sauce = []  # 양념 재료 리스트 (DB 저장용)
        recipe_steps = {}  # 조리 순서 (단계별 저장)
        main_image = ''  # 메인 이미지
        step_images = []  # 단계별 이미지
        final_image = ''  # 완성 이미지
        recipe_time = ''  # 조리 시간
        recipe_tags = []  # 태그

        try:
            # 레시피 제목
            title = soup.find('div', class_='view2_summary')
            recipe_title = title.find('h3').get_text().strip()

            # 메인 이미지
            main_image_tag = soup.find('div', class_='centeredcrop').find('img')
            if main_image_tag:
                main_image = main_image_tag['src']

            # 조리 시간 (정보가 있는 경우)
            time_info = soup.find('span', class_='view2_summary_info1')
            if time_info:
                recipe_time = time_info.get_text().strip()

            # 재료 섹션 구분
            ingredients_section = soup.find('div', class_='ready_ingre3')
            if ingredients_section:
                # "재료"와 "양념" 제목 찾기
                ingredient_headers = ingredients_section.find_all('b')

                # 메인 재료와 양념 구분하기
                ingredient_items = ingredients_section.find_all('ul')
                for idx, ingredient_list in enumerate(ingredient_items):
                    for item in ingredient_list.find_all('li'):
                        ingredient_name = item.find('span').get_text().strip()
                        ingredient_amount = item.get_text().replace(ingredient_name, '').strip()

                        if '양념' in ingredient_headers[idx].get_text():
                            # 양념 재료 추가
                            ingredients_sauce.append((ingredient_name, ingredient_amount))
                        else:
                            # 메인 재료 추가
                            ingredients_main.append((ingredient_name, ingredient_amount))

            # 조리 순서 및 이미지
            steps_section = soup.find('div', class_='view_step')
            if steps_section:
                steps = steps_section.find_all('div', class_='view_step_cont')
                step_images = steps_section.find_all('img')  # 이미지들도 가져오기
                for idx, step in enumerate(steps):
                    step_text = step.get_text().strip()

                    # 각 단계별로 이미지 추가
                    step_image_url = step_images[idx]['src'] if idx < len(step_images) else None

                    recipe_steps[f"Step{idx + 1}"] = {
                        "description": step_text,
                        "image": step_image_url
                    }

            # 완성 이미지 (마지막 이미지로 가정)
            if step_images:
                final_image = step_images[-1]['src']  # 마지막 이미지를 완성 이미지로 저장
                step_images = step_images[:-1]  # 나머지 이미지는 단계별 이미지로 저장

            # 레시피 태그
            tags_section = soup.find('div', class_='view_tag')
            if tags_section:
                tags = tags_section.find_all('a')
                for tag in tags:
                    recipe_tags.append(tag.get_text().strip())

            # 크롤링한 데이터 저장
            recipe_dict = {
                "recipe_title": recipe_title,
                "recipe_time": recipe_time,
                "ingredients_main": ingredients_main,
                "ingredients_sauce": ingredients_sauce,
                "recipe_steps": recipe_steps,
                "main_image": main_image,
                "final_image": final_image,
                "recipe_tags": recipe_tags
            }

            recipe.append(recipe_dict)
            print(f'크롤링 완료: {num}')

        # 존재하지 않는 레시피 건너뜀
        except (AttributeError, IndexError) as e:
            print(f'레시피를 찾을 수 없음: {num}, 에러: {e}')
            continue

    return

if __name__ == "__main__":
    # 사용자로부터 baseUrl 입력 받기
    user_input_url = input('기본 URL을 입력하세요 (예: https://www.10000recipe.com/recipe/6966065): ').strip()

    # baseUrl을 설정하고 시작 및 끝 번호를 추출합니다.
    match = re.match(r'(https://www\.10000recipe\.com/recipe/)(\d+)', user_input_url)
    if match:
        baseUrl = match.group(1)  # 기본 URL 부분
        start_range = int(match.group(2))  # 시작 번호 추출
        end_range = start_range  # 사용자가 입력한 URL 하나에 대해서만 크롤링하려면 시작 번호와 끝 번호가 동일
    else:
        print("잘못된 URL 형식입니다. 올바른 형식을 입력하세요.")
        exit()

    # 페이지 크롤러 실행
    PageCrawler(start_range, end_range)

    # 크롤링한 결과를 JSON 파일로 저장
    recipe_list = {"recipe_list": recipe}
    toJson()
    print("크롤링한 데이터가 recipe.json 파일로 저장되었습니다.")
