# 🌿 Wanna V: OCR을 활용한 영수증 기반 비건 식당 예약 및 리뷰 플랫폼
<div align="center">
  <img src="https://github.com/user-attachments/assets/ab4dd5fe-5bc3-4d48-843f-9f6e2195f2c7" height="200">
</div>

<br>

## 🌟 프로젝트 소개
<img src="https://github.com/user-attachments/assets/baee6b72-dfa0-45ed-8d0d-7b001a03692a" width="750" height="400"><br>

- **비건 식당을 예약**하고 **리뷰를 작성**할 수 있는 플랫폼입니다.
- **식당 추천**과 **검색 조건 필터링**을 활용하여 사용자의 다양한 기호를 반영합니다.
- **영수증 기반의 리뷰**로 고객의 신뢰성을 확보합니다.
- 리뷰 작성에 따른 **리워드 시스템**으로 리뷰 작성을 유도하고 고객을 유지합니다.
- **이벤트**와 **회원등급**을 활용하여 고객을 관리합니다.
- **비건 상품을 판매**하여 매출을 발생하도록 합니다.

[<img src="https://img.shields.io/badge/Admin Repository 바로가기-181717?style=for-the-badge&logo=GitHub&logoColor=white"/>](https://github.com/devyumi/wanna-v-admin)
[<img src="https://img.shields.io/badge/velog 바로가기-20C997?style=for-the-badge&logo=velog&logoColor=white"/>](https://velog.io/@devyumi/팀-OCR을-활용한-영수증-기반-비건-식당-예약-및-리뷰-플랫폼)

<br>

## 👤 팀원 구성 (TEAM: 뷰리풀)
<div align="center">

|**devyumi**|**AAA**|**BBB**|**CCC**|
|:--:|:--:|:--:|:--:|
|<img src="https://github.com/user-attachments/assets/3fbc1f50-87e5-40b6-a805-f7547da20a33" height=170 width=170>|<img src="https://github.com/user-attachments/assets/70e7bd30-8694-4450-b4dc-bed494804a29" height=170 width=170>|<img src="https://github.com/user-attachments/assets/70e7bd30-8694-4450-b4dc-bed494804a29" height=170 width=170> <br>|<img src="https://github.com/user-attachments/assets/70e7bd30-8694-4450-b4dc-bed494804a29" height=170 width=170>|
|OCR 인증·리뷰 관리<br>마이페이지<br>서버 관리|식당 추천<br>메인·레이아웃 관리<br>식당 관리|식당 예약<br>소셜 로그인<br>이벤트·쿠폰 관리|상품 관리<br>결제·챗봇<br>대시보드·감정분석|

</div>

<br>

## 💻 화면 구성
### ✔️ [로고 및 UI 색상](https://github.com/devyumi/wanna-v-user/wiki/%EB%A1%9C%EA%B3%A0-%EB%B0%8F-UI-%EC%83%89%EC%83%81)
<br>

### ✔️ 사용자: 데스크탑 & 모바일 반응형

<div align="center">

|메인|
|:---:|
|<img src="https://github.com/user-attachments/assets/97600c74-c101-4c8f-a463-a86cd0795c88" width="700"/>|

</div>

<div align="center">

|식당 목록|식당 예약|영수증 리뷰 작성|상품 구매|
|:--:|:---:|:---:|:--:|
|<img src="https://github.com/user-attachments/assets/f6e33667-8f8c-4971-8d60-c52bf33b1826" height="300"/>|<img src="https://github.com/user-attachments/assets/bd9057f2-b517-4e33-a8b8-ef645500dffb" height="300"/>|<img src="https://github.com/user-attachments/assets/a279540b-c2e7-4efc-84a3-8462a3ce79a6" height="300"/>|<img src="https://github.com/user-attachments/assets/1e08af3c-f8ae-4222-b2ff-0b5db893d3a8" height="300"/>|

</div>

<br>

### ✔️ 관리자
<div align="center">

|대시보드|식당 관리|리뷰 관리|
|:--:|:--:|:--:|
|<img src="https://github.com/user-attachments/assets/c5e2ee26-9e8e-4704-8a37-7198884a4107" width="500"/>|<img src="https://github.com/user-attachments/assets/6f7821a7-ff72-4a25-98a4-f78c6506608f" width="500"/>|<img src="https://github.com/user-attachments/assets/90171129-1954-4f93-8df3-e77925bf3792" width="500"/>|
|<img src="https://github.com/user-attachments/assets/1214a98c-395e-42c3-b184-86c3e4d73bfc" width="500">|<img src="https://github.com/user-attachments/assets/8ca11b51-92b6-454d-87f7-90ca396dee73" width="500">|

</div>

<br><br>

## 🎨 주요 기능

### ➡️ 식당 추천

`위치 기반`, `가격대 별`, `이번 주 추천`, `사용자 선호도 기반`, `비건 등급` 등 다양한 기준으로 식당을 추천합니다.
```
1) 위치 기반: 사용자 현재 위치
2) 가격대 별: 식당 음식의 평균 가격
3) 이번 주 추천: 사용자 현재 위치 기반 모든 식당 줌 랜덤 추천
4) 사용자 선호도 기반: 좋아요, 리뷰, 평점 높은 순
5) 비건 등급: 유제품, 계란, 동물의 알, 생선, 닭고기, 붉은 고기 포함 총 6듣급
```

<br>

### ➡️ 식당 목록

`비건 등급`, `평균 가격대`, `식당 분류`, `제공 서비스`, `분위기`, `별점` 등 다양한 필터링을 제공하여 사용자가 기호에 맞는 식당을 찾을 수 있습니다.

<br>

### ➡️ 식당 예약

`여는 시간`, `닫는 시간`, `라스트 오더 시간`, `브레이크 타임 시간`, `휴무일`을 고려하여 식당을 예약할 수 있습니다.

예약금은 토스 페이로 결제할 수 있습니다.

<br>

### ➡️ 영수증 리뷰 작성

리뷰의 신뢰성을 위해 반드시 영수증을 인증한 뒤 리뷰를 작성할 수 있습니다.

포인트를 얻기 위한 목적으로 무분별한 리뷰를 작성하는 행위를 방지하기 위해 리뷰 수정과 삭제에 제한을 두었습니다.

욕설 및 비속어를 필터링하여 깨끗한 리뷰 시스템을 유지할 수 있도록 합니다.

<br>

### ➡️ 비건 상품 구매

리뷰 작성 후 얻을 수 있는 리워드와 이벤트성 쿠폰을 활용하여 비건 상품(밀키트, 화장품, 간식)을 구매할 수 있습니다.

<br><br>

## ⚙ 개발 환경
### ✔️ Front
<div>
<img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white"> 
<img src="https://img.shields.io/badge/css3-1572B6?style=for-the-badge&logo=css3&logoColor=white"> 
<img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"> 
</div>
  
### ✔️ Back
<div>
<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white"/>
<img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=Thymeleaf&logoColor=white"/>
<img src="https://img.shields.io/badge/Redis-FF4438?style=for-the-badge&logo=Redis&logoColor=white"/>
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/JPA-6DB33F?style=for-the-badge&logo=JPA&logoColor=white"/>
<img src="https://img.shields.io/badge/Querydsl-4479A1?style=for-the-badge&logo=Querydsl&logoColor=white"/>
<img src="https://img.shields.io/badge/MyBatis3-F36633?style=for-the-badge&logo=MyBatis3&logoColor=white"/>
<img src="https://img.shields.io/badge/Junit5-25A162?style=for-the-badge&logo=Junit5&logoColor=white"/>
<img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=Postman&logoColor=white"/>  
</div>

### ✔️ OS/Server
<div>
<img src="https://img.shields.io/badge/Linux-FCC624?style=for-the-badge&logo=Linux&logoColor=white"/>
<img src="https://img.shields.io/badge/Naver Cloud Platform-03C75A?style=for-the-badge&logo=Naver&logoColor=white"/>
<img src="https://img.shields.io/badge/NGINX-009639?style=for-the-badge&logo=NGINX&logoColor=white"/>
<img src="https://img.shields.io/badge/Termius-000000?style=for-the-badge&logo=Termius&logoColor=white"/>  
</div>

### ✔️ Communication
<div>
<img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white"> 
<img src="https://img.shields.io/badge/slack-4A154B?style=for-the-badge&logo=slack&logoColor=white"> 
<img src="https://img.shields.io/badge/google drive-4285F4?style=for-the-badge&logo=google drive&logoColor=white">
<img src="https://img.shields.io/badge/Figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white"> 
</div>

### ✔️ [Code Convention](https://github.com/devyumi/wanna-v-user/wiki/Code-Convention)

### ✔️ [Git Branch 전략](https://github.com/devyumi/wanna-v-user/wiki/Git-Branch-%EC%A0%84%EB%9E%B5)

### ✔️ [Git Commit Message Rules](https://github.com/devyumi/wanna-v-user/wiki/Git-Commit-Message-Rules)

<br>

## 🚀 서비스 아키텍처
<img src="https://github.com/user-attachments/assets/57f03417-1392-486f-96e9-16788565f51f" hegith="200"><br><br>


## 🧩 트러블 슈팅
- [MySQL 서브쿼리 연산자 내 LIMIT 사용 불가와 ONLY_FULL_GROUP_BY](https://github.com/devyumi/wanna-v-user/wiki/Trouble-Shooting:-MySQL-%EC%84%9C%EB%B8%8C%EC%BF%BC%EB%A6%AC-%EC%97%B0%EC%82%B0%EC%9E%90-%EB%82%B4-LIMIT-%EC%82%AC%EC%9A%A9-%EB%B6%88%EA%B0%80%EC%99%80-ONLY_FULL_GROUP_BY)
- [NCP 서버 세팅 과정](https://velog.io/@devyumi/NCP-Ubuntu-서버-세팅-방법)
- [Spring Boot 배포](https://velog.io/@devyumi/NCP-서버에서-Spring-Boot-배포하기)
