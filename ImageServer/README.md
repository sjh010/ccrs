# DB 스키마
 - src/test/resources/imageServerDB.sql

# 로컬에 암호화dll적용방법
 - 원도우 시스템 변수 PATH 에 dll 위치 등록 ( C:\DATA\sources\ImageServer\data )
 - 재부팅
   
# 빌드방법:
 - Export..로 war 파일 출력
 
# 수정시 빌드방법
 - image_core maven 빌드 (tisis nexus에  imageCore.jar 파일이 업로드됨)
 - imageServer/pom.xml 에서 imageCore 버전확인 후 maven update