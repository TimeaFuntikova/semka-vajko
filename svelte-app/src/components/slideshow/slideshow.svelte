<script>
    let slideIndex = 1;
    function showSlides(n) {
        let i;
        const slides = document.getElementsByClassName("mySlides");
        const dots = document.getElementsByClassName("dot");

        if (n > slides.length) { slideIndex = 1 }
        if (n < 1) { slideIndex = slides.length }

        for (i = 0; i < slides.length; i++) {
            slides[i].style.display = "none";
        }
        for (i = 0; i < dots.length; i++) {
            dots[i].className = dots[i].className.replace(" active", "");
        }
        slides[slideIndex - 1].style.display = "block";
        dots[slideIndex - 1].className += " active";
    }

    $: showSlides(slideIndex);

    function plusSlides(n) {
        showSlides(slideIndex += n);
    }

    function currentSlide(n) {
        showSlides(slideIndex = n);
    }
</script>

<div class="slideshow-container">
    <!-- Slide 1 -->
    <div class="mySlides fade">
        <div class="numbertext">1 / 3</div>
        <img src="../pages/cfee3d4d24fbee45eab40e18954c6e58.jpg" style="width:100%">
        <div class="text">Caption for the first image</div>
    </div>

    <!-- Slide 2 -->
    <div class="mySlides fade">
        <div class="numbertext">2 / 3</div>
        <img src="../pages/cfee3d4d24fbee45eab40e18954c6e58.jpg" style="width:100%"/>
        <div class="text">Caption for the second image</div>
    </div>

    <!-- Slide 3 -->
    <div class="mySlides fade">
        <div class="numbertext">3 / 3</div>
        <img src="../pages/cfee3d4d24fbee45eab40e18954c6e58.jpg" style="width:100%">
        <div class="text">Caption for the third image</div>
    </div>

    <!-- Next and previous buttons -->
    <a class="prev" onclick="plusSlides(-1)">&#10094;</a>
    <a class="next" onclick="plusSlides(1)">&#10095;</a>
</div>

<br>

<div style="text-align:center">
    <span class="dot" on:click={() => currentSlide(1)}></span>
    <span class="dot" on:click={() => currentSlide(2)}></span>
    <span class="dot" on:click={() => currentSlide(3)}></span>
</div>
