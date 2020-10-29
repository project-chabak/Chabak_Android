## 네이밍 규칙
- 클래스 이름 => UpperCamelCase방식
	ex) MainActivity
	
- 메소드 이름 => loserCamelCase방식
	ex) initMap
	
- 파라미터 이름 & 로컬 변수 이름 => lowerCamelCase
	ex) btnClose
	
- xml 객체 id명 => snake case
	ex) btn_close
  
  * xml 위젯 id명  : 객체 _ 기능(역할)
    	ex) 닫기버튼의 id명 : btn_close
  * xml 위젯를 참조하는 변수 선언 : 객체 id명을 lowerCamelCase방식으로 표현
    	ex) 닫기버튼을 가르키는 변수 : btnClose
  

  
    	**위젯별 줄임말 규칙
          Button -> btn
          EditText -> edt
          TextView -> tv
          ImageView -> img
          RecyclerView -> rv
          ...
	  

## 기타
- 화면레이아웃 - 가능하면 Constraintlayout 사용(기기에따라 레이아웃이 변형되는 문제를 최소화하기위해)
- drawable 파일 - drawable 파일 추가할 때 drawable-v24말고 drawable에 넣어주세요!
