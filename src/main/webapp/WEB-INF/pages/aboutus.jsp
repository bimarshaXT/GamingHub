<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/aboutus.css?v=1.1">
<meta charset="UTF-8">
<title>About Us</title>
</head>
<body>

<%@ include file="/WEB-INF/pages/header.jsp" %>

<form action = "${pageContext.request.contextPath}/aboutus" method="get"> 
<div class="hero">
        <div class="hero-content">
            <h1>About GamingHub</h1>
            <div class="hero-line"></div>
            <p>Your premier destination for premium gaming hardware and peripherals</p>
        </div>
    </div>

    <div class="container">
        <section class="about-section">
            <h2 class="section-title">Our Story</h2>
            <div class="about-grid">
                <div class="about-image">
                    <img src="${pageContext.request.contextPath}/resources/images/HQ.jpg" alt="GamingHub Headquarters">
                </div>
                <div class="about-text">
                    <p>Founded in 2015, GamingHub began with a simple mission: to provide gamers with the highest quality gaming hardware at competitive prices. What started as a small online store has grown into a trusted name in the gaming community.</p>
                    <p>Our team consists of passionate gamers who understand the importance of reliable equipment that enhances your gaming experience. We test every product we sell to ensure it meets our strict quality standards before it reaches your hands.</p>
                    <p>At GamingHub, we believe that exceptional gaming experiences require exceptional hardware. That's why we partner with the most innovative and respected manufacturers in the industry to bring you cutting-edge technology that pushes boundaries and elevates your gameplay.</p>
                </div>
            </div>
        </section>

        <section class="about-section">
            <h2 class="section-title">Our Vision</h2>
            <p>We aim to be the leading destination for gaming enthusiasts seeking premium hardware solutions that enhance their gaming experience. Our commitment to quality, innovation, and customer satisfaction drives everything we do.</p>
            <p>GamingHub strives to foster a community where gamers can find not just products, but also resources, support, and fellow enthusiasts who share their passion. We're not just selling hardware; we're building a gaming ecosystem.</p>
        </section>

        <section class="about-section">
            <h2 class="section-title">Meet Our Team</h2>
            <div class="team-grid">
                <div class="team-member">
                    <img src="${pageContext.request.contextPath}/resources/images/download.png" alt="Team Member">
                    <div class="team-info">
                        <h3>Alex Johnson</h3>
                        <p>Founder & CEO</p>
                        <div class="bio">Professional gamer turned entrepreneur with a passion for building gaming communities.</div>
                    </div>
                </div>
                <div class="team-member">
                    <img src="${pageContext.request.contextPath}/resources/images/download.png" alt="Team Member">
                    <div class="team-info">
                        <h3>Sarah Chen</h3>
                        <p>Lead Hardware Specialist</p>
                        <div class="bio">Computer engineer with 10+ years experience in gaming hardware optimization.</div>
                    </div>
                </div>
                <div class="team-member">
                    <img src="${pageContext.request.contextPath}/resources/images/download.png" alt="Team Member">
                    <div class="team-info">
                        <h3>Marcus Reid</h3>
                        <p>Customer Experience Manager</p>
                        <div class="bio">Dedicated to ensuring every customer has an exceptional shopping experience.</div>
                    </div>
                </div>
                
            	</div>
        	</section>
    </div>
    </form>
    <%@ include file="/WEB-INF/pages/footer.jsp" %>
</body>

</html>