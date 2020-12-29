//fetch image full path and send it to android
var header = document.getElementById("myHeader");
images = document.getElementsByTagName("img");
//AndroidCallback.getImageName(images[0].src);

var headerColor = 0;
//android will call this method to set the color of elements h2,h3,and inspiration class elements
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

function readMoreFunction(event) {
  var target = event.target;
  var more =  target.previousElementSibling;
  var dots = more.previousElementSibling;

  if (dots.style.display === "none") {
    dots.style.display = "inline";
    target.innerHTML = "קרא עוד";
    more.style.display = "none";
    } else {
    dots.style.display = "none";
    target.innerHTML = "הסתר";
    more.style.display = "inline";
    }
}

