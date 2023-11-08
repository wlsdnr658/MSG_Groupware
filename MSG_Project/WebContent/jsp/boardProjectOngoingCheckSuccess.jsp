<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
	if(window.name=="update"){
		window.opener.parent.location.href="boardProjectOngoingModifyForm?boardNo=${projectOngoing.boardNo}";
	}else if(window.name=="delete"){
		window.opener.parent.location.href="boardProjectOngoingDelete?boardNo=${projectOngoing.boardNo}";
	}
	
	window.close();	
</script>