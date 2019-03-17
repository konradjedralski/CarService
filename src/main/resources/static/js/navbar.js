$(function () {
    var headerHeight = $('.header').outerHeight();

    $(window).bind('scroll', function () {
        if ($(window).scrollTop() > headerHeight) {
            $('#navbar').removeClass('navbar-default');
            $('#navbar').addClass('navbar-fixed-top');
        } else {
            $('#navbar').removeClass('navbar-fixed-top');
            $('#navbar').addClass('navbar-default');
        }
    });
});