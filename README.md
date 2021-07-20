# FirebaseLogin
파이어베이스의 authentication과 realtime Database를 이용한 회원관리 실습 프로젝트

## Introduction
회원관리를 위한 안드로이드 어플리케이션이다.
1. 회원가입 기능(이메일 사용)
2. 비밀번호 찾기 기능 (가입된 이메일로 비밀번호 재설정 가능)
3. 로그인 기능
4. 회원 정보 수정 기능
5. 로그아웃 기능
6. 회원 탈퇴 기능

## Development Environment
* Android Studio
* Firebase

## Learning about
* Java String 비교 
  - .equals : String의 내용 비교
  - == : 객체의 주소값 비교
* Firebase Token
  - 앱이 최초로 로드 될 때 모바일에서 생성되는 값
  - 특정 규칙을 가진 문자/숫자열로 구성
  - 토큰 값을 이용하여 child 생성 시 이름으로 사용
  - 수정 시 업데이트 모바일이 실행하면 child내 토큰 값중 자신의 토큰 값과 일치하는 부분 찾아서 업데이트 
* header NULL 오류
  - wifi 문제일 경우 많음
