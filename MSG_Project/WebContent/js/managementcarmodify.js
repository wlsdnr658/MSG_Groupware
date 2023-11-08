$(function() {
		$(".delete-btn").on("click", function() {
			var context = $("#context").val();

			var rendNo = $(this).next().val();
			var typeName =$(this).next().next().val();


			var url = context + "/management/delete?rendNo=" + rendNo+"&typeName="+typeName;

			$(location).attr('href', url);
		});

	});