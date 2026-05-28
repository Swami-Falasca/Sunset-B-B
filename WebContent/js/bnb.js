function slidePrev(btn) {
    const container = btn.closest('.slideshow-container');
    const slides = container.querySelectorAll('img');
    let activeIndex = Array.from(slides).findIndex(img => img.classList.contains('active'));
    slides[activeIndex].classList.remove('active');
    activeIndex = (activeIndex - 1 + slides.length) % slides.length;
    slides[activeIndex].classList.add('active');
}

function slideNext(btn) {
    const container = btn.closest('.slideshow-container');
    const slides = container.querySelectorAll('img');
    let activeIndex = Array.from(slides).findIndex(img => img.classList.contains('active'));
    slides[activeIndex].classList.remove('active');
    activeIndex = (activeIndex + 1) % slides.length;
    slides[activeIndex].classList.add('active');
}

document.addEventListener("DOMContentLoaded", () => {
    const lightbox = document.getElementById("lightbox");
    const lightboxImg = document.getElementById("lightbox-img");
    const closeBtn = document.querySelector(".close");
    const nextBtn = document.querySelector(".lightbox-next");
    const prevBtn = document.querySelector(".lightbox-prev");

    let currentSlides = [], currentIndex = 0;

    document.querySelectorAll('.slideshow-container').forEach(container => {
        const slides = container.querySelectorAll('img');
        slides.forEach((img, i) => {
            img.addEventListener("click", () => {
                currentSlides = Array.from(slides);
                currentIndex = i;
                lightbox.style.display = "flex";
                lightboxImg.src = currentSlides[currentIndex].src;
            });
        });
    });

    closeBtn.onclick = () => lightbox.style.display = "none";

    nextBtn.onclick = () => {
        currentIndex = (currentIndex + 1) % currentSlides.length;
        lightboxImg.src = currentSlides[currentIndex].src;
    };

    prevBtn.onclick = () => {
        currentIndex = (currentIndex - 1 + currentSlides.length) % currentSlides.length;
        lightboxImg.src = currentSlides[currentIndex].src;
    };

    lightbox.addEventListener("click", e => {
        if (e.target === lightbox) lightbox.style.display = "none";
    });

    document.addEventListener("keydown", e => {
        if (lightbox.style.display === "flex") {
            if (e.key === "ArrowRight") nextBtn.onclick();
            else if (e.key === "ArrowLeft") prevBtn.onclick();
            else if (e.key === "Escape") closeBtn.onclick();
        }
    });
});