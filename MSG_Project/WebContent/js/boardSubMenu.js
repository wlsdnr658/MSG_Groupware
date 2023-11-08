$(document).ready(function() {
			$(".menu>a").click(function() {
				var submenu = $(this).next("ul");

				if (submenu.is(":visible")) {
					submenu.slideUp(1);
				} else {
					submenu.slideDown(1);
				}
			});
		});


$(document).ready(function() {
	$("#menu>a").click(function() {
		var submenu = $(this).next("ul");

		if (submenu.is(":visible")) {
			submenu.slideUp(1);
		} else {
			submenu.slideDown(1);
		}
	});
});