<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
	if(window.name=="update"){
		window.opener.parent.location.href="boardNoticeModifyForm?boardNo=${notice.boardNo}";	
	}else if(window.name=="delete"){ 
		window.opener.parent.location.href="boardNoticeDelete?boardNo=${notice.boardNo}";
	}else if(window.name=="e_update"){
		window.opener.parent.location.href="boardEventModifyForm?boardNo=${event.boardNo}";
	}else if(window.name=="e_delete"){
		window.opener.parent.location.href="boardEventDelete?boardNo=${event.boardNo}";
	}
	
	
	window.close();	
	
	
</script>