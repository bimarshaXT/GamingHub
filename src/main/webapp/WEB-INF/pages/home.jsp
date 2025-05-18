<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>GamingHub</title>

    <!-- CSS files -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home.css?v=1.2.5">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>

<body>

    <!-- Include Header -->
    <%@ include file="/WEB-INF/pages/header.jsp" %>

    <!-- Slider Section (NO BUTTON) -->
    <div class="simple-slider">
        <img id="slider-image" src="" alt="Slide" />
        <div class="text-overlay">
            <h2 id="slider-title"></h2>
            <p id="slider-subtitle"></p>
        </div>
    </div>

    <!-- Product Categories Section -->
    <section class="product-categories">
        <div class="container">
            <div class="categories-grid">
                <!-- Row 1 -->
                <a href="${pageContext.request.contextPath}/product?category=3" class="category-item large">
                    <div class="category-content">
                        <h3>GAMING KEYBOARDS</h3>
                        <span class="shop-now">SHOP NOW ›</span>
                    </div>
                    <img src="resources/images/keyboard-ad.jpg" alt="Gaming Keyboards">
                </a>

                <a href="${pageContext.request.contextPath}/product?category=5" class="category-item medium">
                    <div class="category-content">
                        <h3>GAMING CHAIRS</h3>
                        <span class="shop-now">SHOP NOW ›</span>
                    </div>
                    <img src="resources/images/chair-ad.avif" alt="Gaming Chairs">
                </a>

                <a href="${pageContext.request.contextPath}/product?category=2" class="category-item medium">
                    <div class="category-content">
                        <h3>PROCESSOR</h3>
                        <span class="shop-now">SHOP NOW ›</span>
                    </div>
                    <img src="resources/images/processor-ad.jpg" alt="Processors">
                </a>

                <!-- Row 2 -->
                <a href="${pageContext.request.contextPath}/product?category=4" class="category-item medium">
                    <div class="category-content">
                        <h3>GAMING MICE</h3>
                        <span class="shop-now">SHOP NOW ›</span>
                    </div>
                    <img src="resources/images/mouse-ad.jpeg" alt="Gaming Mice">
                </a>

                <a href="${pageContext.request.contextPath}/product?category=6" class="category-item medium">
                    <div class="category-content">
                        <h3>GAMING DESKS</h3>
                        <span class="shop-now">SHOP NOW ›</span>
                    </div>
                    <img src="resources/images/desk-ad.jpeg" alt="Gaming Desks">
                </a>

                <a href="${pageContext.request.contextPath}/product?category=1" class="category-item large bottom">
                    <div class="category-content">
                        <h3>GAMING MONITOR</h3>
                        <span class="shop-now">SHOP NOW ›</span>
                    </div>
                    <img src="resources/images/monitor-ad.jpg" alt="Gaming Monitors">
                </a>
            </div>
        </div>
    </section>

    <!-- Slider Script (NO BUTTON) -->
    <script>
        const slides = [
            {
                src: "resources/images/background3.png",
                title: "Explore the Latest Gaming Gear",
                subtitle: "Discover top-rated products and elevate your gaming experience."
            },
            {
                src: "resources/images/background4.webp",
                title: "Top Deals This Season",
                subtitle: "Get unbeatable discounts on must-have gaming essentials."
            },
            {
                src: "resources/images/registrationBg.jpeg",
                title: "Build Your Perfect Setup",
                subtitle: "From accessories to complete rigs, customize your battlestation today."
            }
        ];

        let currentIndex = 0;

        function showSlide(index) {
            const slide = slides[index];
            document.getElementById("slider-image").src = slide.src;
            document.getElementById("slider-title").textContent = slide.title;
            document.getElementById("slider-subtitle").textContent = slide.subtitle;
        }

        function autoSlide() {
            currentIndex = (currentIndex + 1) % slides.length;
            showSlide(currentIndex);
        }

        // Initial load
        showSlide(currentIndex);
        setInterval(autoSlide, 5000);
    </script>

    <!-- Include Footer -->
    <%@ include file="/WEB-INF/pages/footer.jsp" %>

</body>
</html>
