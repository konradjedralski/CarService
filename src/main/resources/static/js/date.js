function startTime() {
    var day = ['Niedziela', 'Poniedziałek', 'Wtorek', 'Środa', 'Czwartek', 'Piątek', 'Sobota'];
    var today = new Date();
    var w = today.getDay();
    var d = today.getDate();
    var m = today.getMonth() + 1;
    var y = today.getFullYear();
    var h = today.getHours();
    var min = today.getMinutes();
    var s = today.getSeconds();
    d = checkUnder(d);
    m = checkUnder(m);
    h = checkUnder(h);
    min = checkUnder(min);
    s = checkUnder(s);
    document.getElementById('txt').innerHTML =
    day[w] + ", " + d + "/" + m + "/" + y + ", "+ h + ":" + min + ":" + s;
    var t = setTimeout(startTime, 500);

}
function checkUnder(i) {
    if (i < 10) {i = "0" + i};  // add zero in front of numbers < 10
    return i;
}