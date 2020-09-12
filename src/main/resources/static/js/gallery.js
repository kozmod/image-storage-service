function displayFunk(imgs) {
    const expandImg = document.getElementById("expandedImg");
    const imgText = document.getElementById("imgtext");
    expandImg.src = imgs.src;
    imgText.innerHTML = imgs.alt;
    expandImg.parentElement.style.display = "block";
}