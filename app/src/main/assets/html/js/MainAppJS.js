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
var counter = 1;
//This method is for the read more or hide functionality in days instructions
function readMoreFunction(event) {
  var target = event.target;
  var more =  target.previousElementSibling;
  var dots = more.previousElementSibling;
  if (!dots.hasAttribute("id")) {
    var newID =  "" + counter++;
    dots.setAttribute("id",newID);
  }

  if (dots.style.display === "none") {
    dots.style.display = "inline";
    target.innerHTML = "קרא עוד";
    more.style.display = "none";
    window.console.log("jump to:"+ dots["id"]);
    window.location.href = "#"+dots["id"];
    } else {
    dots.style.display = "none";
    target.innerHTML = "הסתר";
    more.style.display = "inline";

    }
}

//get the browser version and act accordingly
var ua = navigator.userAgent;
var chromeVersion = -1;
if (/Chrome\/(\S+)/.test(ua)) {
      var  ver = RegExp["$1"];
      chromeVersion = parseFloat(ver);
}
window.console.log("chromeVersion:"+chromeVersion);
if(chromeVersion>44) {//in case of version 74 and more make space bewteen containt and the toolbar
    var bodies = document.getElementsByTagName("body");
    bodies[0].classList.add("body-toolbar-fix");
}
else {//fix lower version of browser: remove background and nested div (container) and circular--swami class that cause problem
    var headers = document.getElementsByClassName("header");
    if(headers) {
        var presented_url = ""+window.location;
        var page_url = presented_url.substring(presented_url.lastIndexOf("/")+1);
        if(page_url.startsWith("day")) {
            var picDiv = headers[0];
            var container = picDiv.parentElement;
            var center = container.parentElement;
            center.removeChild(container);
            center.appendChild(picDiv);
         } else {
            var picDiv = headers[0];
            var container = picDiv.parentElement;
            var center = container.parentElement;
            center.removeChild(container);
            center.appendChild(picDiv);
            if(center.classList.contains("center-class")) {
                center.classList.remove("center-class");
                center.classList.add("center-class-old");
            }
            if(picDiv.classList.contains("circular--swami")) {
                   picDiv.classList.remove("circular--swami");
            }
           if(picDiv.classList.contains("header")) {
                 picDiv.classList.remove("header");
                 picDiv.classList.add("header-old");
           }

         }
     }
}
