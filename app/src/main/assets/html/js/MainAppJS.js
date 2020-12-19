var header = document.getElementById("myHeader");
images = document.getElementsByTagName("img");
AndroidCallback.getImageName(images[0].src);

var headerColor = 0;
function updateHeaders(num1, num2, num3) {
    var headers2 = document.getElementsByTagName("h2");
    for (i = 0; i < headers2.length; i++) {
        headers2[i].style.color = "hsl("+Math.floor(num1)+","+Math.floor(num2*100)+"%,"+Math.floor(num3*100)+"%)";
        headerColor = "hsl("+Math.floor(num1)+","+Math.floor(num2*100)+"%,"+Math.floor(num3*100)+"%)";
     }

         var headers3 = document.getElementsByTagName("h3");
         for (i = 0; i < headers3.length; i++) {
             headers3[i].style.color = "hsl("+Math.floor(num1)+","+Math.floor(num2*100)+"%,"+Math.floor(num3*100)+"%)";
         }

         var inspirations = document.getElementsByClassName("inspiration");
         for (i = 0; i < inspirations.length; i++) {
             inspirations[i].style.color = "hsl("+Math.floor(num1)+","+Math.floor(num2*100)+"%,"+Math.floor(num3*100)+"%)";
         }

}

function readMoreFunction() {
  var dots = document.getElementById("dots");
  var dots2 = document.getElementById("dots2");
  var moreText = document.getElementById("more");
  var moreText2 = document.getElementById("more2");
  var btnText = document.getElementById("myBtn");
  var btnText2 = document.getElementById("myBtn2");

  if (dots.style.display === "none") {
    dots.style.display = "inline";
    btnText.innerHTML = "קרא עוד";
    moreText.style.display = "none";
    } else {
    dots.style.display = "none";
    btnText.innerHTML = "הסתר";
    moreText.style.display = "inline";
    }
  if (dots2.style.display === "none") {
      dots2.style.display = "inline";
      btnText2.innerHTML = "קרא עוד";
      moreText2.style.display = "none";
    } else {
      dots2.style.display = "none";
      btnText2.innerHTML = "הסתר";
      moreText2.style.display = "inline";
    }
}
function readMoreFunction2() {

  var dots2 = document.getElementById("dots2");

  var moreText2 = document.getElementById("more2");

  var btnText2 = document.getElementById("myBtn2");

   if (dots2.style.display === "none") {
      dots2.style.display = "inline";
      btnText2.innerHTML = "קרא עוד";
      moreText2.style.display = "none";
    } else {
      dots2.style.display = "none";
      btnText2.innerHTML = "הסתר";
      moreText2.style.display = "inline";
    }
}