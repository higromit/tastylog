<div align="center">

# 📕Tastylog
핸즈프리 요리 어시스턴트: 웹 레시피 맞춤 수정과 음성 안내 어플리케이션

</div> 


## 📝 Introduction
"손끝 하나 없이 요리하는 스마트한 레시피 북, **"Tastylog"**

**Tastylog**는 웹사이트에서 좋아하는 레시피를 쉽게 가져와 한 곳에 모은 후, 음성 명령으로 레시피를 단계별로 확인할 수 있는 앱입니다. 여러 사이트를 돌아다닐 필요 없이, 나만의 레시피를 기록하고 관리할 수 있습니다. "Tastylog"와 함께 요리의 즐거움을 더해보세요!


<br />


#### "웹사이트에서 레시피 URL을 입력하면 원하는 레시피를 어플리케이션으로 가져와, 자신의 취향에 맞게 수정"
|레시피 웹사이트에서 가져오기|웹사이트에서 가져온 레시피 모습|
|:---:|:---:|
|<img src="https://github.com/higromit/tastylog/blob/main/assets/recipe_input%20UI.jpeg" width="400"/>|<img src="https://github.com/higromit/tastylog/blob/main/assets/recipe_inputUI2.jpeg" width="400"/>|

<br />


#### "저장한 레시피를 마이크 버튼을 눌러 '다음' 또는 '이전'과 같은 음성 명령으로 레시피 단계를 쉽게 조회"
|레시피 조회 이미지(재료)|레시피 조회 이미지(요리단계)|
|:---:|:---:|
|<img src="https://github.com/higromit/tastylog/blob/main/assets/recipe_searchUI.jpeg" width="400"/>|<img src="https://github.com/higromit/tastylog/blob/main/assets/recipe_searchUI2.jpeg" width="400"/>|



<br />


### ERD
<img src="https://github.com/higromit/tastylog/blob/main/assets/tastylog_ERD.png">


<br />


## 🗂️ APIs


➀ **레시피 관리**
  <br />
 	⦁ GET 	     /api/recipe/manual: 레시피 리스트 조회 
  <br />
	⦁ GET      /api/recipe/manual/{id}: 한 개의 특정 레시피를 조회
  <br />
	⦁ POST    /api/recipe/manual: 한 개의 새로운 레시피 생성
  <br />
	⦁ PUT     /api/recipe/manual/{id}: 한 개의 레시피를 수정
  <br />
	⦁ DELETE /api/recipe/manual/{id}: 한 개의 레시피를 삭제<br /><br />



    
 
➁ **웹사이트 레시피 크롤링**<br />
	⦁ POST  /api/recipe/website/crawlSave: 웹사이트 레시피 크롤링 및 저장<br /><br />


➂ **유튜브 레시피 크롤링**<br />
	⦁ POST /api/recipe/website/crawlYoutube: 웹사이트 레시피 크롤링 


<br />

## ⚙ 기술 스택
### Back-end
<div>
    <img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/Java.png?raw=true" width="80">
    <img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/SpringBoot.png?raw=true" width="80">
    <img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/SpringSecurity.png?raw=true" width="80">
    <img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/SpringDataJPA.png?raw=true" width="80">
    <img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/Mysql.png?raw=true" width="80">
</div>

### Tools
<div>
    <img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/Github.png?raw=true" width="80">
    <img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/Notion.png?raw=true" width="80">
</div>





<br />

## 💁‍♂️ 프로젝트 팀원
|Backend|
|:---:|
<a href="https://github.com/higromit"><img src="https://avatars.githubusercontent.com/u/146078058?v=4" width="100px"><br>김수현</a>
