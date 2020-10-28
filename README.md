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