$(document).ready(function () {
	$("#logoutLink").on("click", function (e) {
		e.preventDefault();
		document.logoutForm.submit();
	});

	customizeDropDown();
});

function customizeDropDown() {

	$(".navbar .dropdown").hover(function() {
		$(this).toggleClass("active").next().stop(true, true).slideToggele();
	});

	$(".dropdown > a").click(function() {
		location.href = this.href;
	});
}