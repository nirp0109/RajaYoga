window.onscroll = function() {myFunction()};
var header = document.getElementById("myHeader");

images = document.getElementsByTagName("img");
AndroidCallback.getImageName(images[0].src);

var headerColor = 0;
var sentenceColor=0;
function updateHeaders(num1, num2, num3) {
    headers2 = document.getElementsByTagName("h2");
    sentence=document.getElementsByClassName("inspiration");
    for (i = 0; i < headers2.length; i++) {
        headers2[i].style.color = "hsl("+Math.floor(num1)+","+Math.floor(num2*100)+"%,"+Math.floor(num3*100)+"%)";
        headerColor = "hsl("+Math.floor(num1)+","+Math.floor(num2*100)+"%,"+Math.floor(num3*100)+"%)";
     }

    sentence.style.color = "hsl("+Math.floor(num1)+","+Math.floor(num2*100)+"%,"+Math.floor(num3*100)+"%)";
   sentenceColor = "hsl("+Math.floor(num1)+","+Math.floor(num2*100)+"%,"+Math.floor(num3*100)+"%)";
}


var sticky = header.offsetTop;
function myFunction() {
  var scrollTop =  window.pageYOffset;
  if (scrollTop > sticky) {
    header.classList.add("sticky");
  } else {
    header.classList.remove("sticky");
  }
}

function openTab(evt, tabName) {
  var i, tabcontent, tablinks;
  tabcontent = document.getElementsByClassName("tabcontent");
  for (i = 0; i < tabcontent.length; i++) {
    tabcontent[i].style.display = "none";
  }
  tablinks = document.getElementsByClassName("tablinks");
  for (i = 0; i < tablinks.length; i++) {
    tablinks[i].className = tablinks[i].className.replace(" active", "");
  }
  document.getElementById(tabName).style.display = "block";
  evt.currentTarget.className += " active";
}

buttons = document.getElementsByClassName("tablinks");
buttons[0].click();

function readMoreFunction() {
  var dots = document.getElementById("dots");
  var moreText = document.getElementById("more");
  var btnText = document.getElementById("myBtn");

  if (dots.style.display === "none") {
    dots.style.display = "inline";
    btnText.innerHTML = "קרא עוד";
    moreText.style.display = "none";
    } else {
    dots.style.display = "none";
    btnText.innerHTML = "הסתר";
    moreText.style.display = "inline";
    }

}
function readMoreFunction2() {
  var dots = document.getElementById("dots2");
  var moreText = document.getElementById("more2");
  var btnText = document.getElementById("myBtn2");
   if (dots.style.display === "none") {
      dots.style.display = "inline";
      btnText.innerHTML = "קרא עוד";
      moreText.style.display = "none";
    } else {
      dots.style.display = "none";
      btnText.innerHTML = "הסתר";
      moreText.style.display = "inline";
    }
}