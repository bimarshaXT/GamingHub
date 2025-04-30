<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Contact Us</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/contactus.css?v=1.1">
</head>
<body>
<%@ include file="/WEB-INF/pages/header.jsp" %>

<
<div class="hero">
        <div class="hero-content">
            <h1>Contact Us</h1>
            <div class="hero-line"></div>
            <p>We're here to help with all your gaming needs</p>
        </div>
    </div>

    <div class="container">
        <section class="contact-section">
            <h2 class="section-title">Get In Touch</h2>
            <p>Have questions about our products or services? Need technical support? Our team is ready to assist you.</p>
            
            <div class="contact-grid">
                <div class="contact-form">
                    <form action = "${pageContext.request.contextPath}/aboutus" method="get">
                        <div class="form-group">
                            <label for="name">Your Name</label>
                            <input type="text" id="name" name="name" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label for="email">Email Address</label>
                            <input type="email" id="email" name="email" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label for="subject">Subject</label>
                            <input type="text" id="subject" name="subject" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label for="message">Your Message</label>
                            <textarea id="message" name="message" class="form-control" required></textarea>
                        </div>
                        <button type="submit" class="btn">Send Message</button>
                    </form>
                </div>
                
                <div class="contact-info">
                    <div class="info-item">
                        <div class="info-icon">
                            
                        </div>
                        <div class="info-text">
                            <h4>Our Location</h4>
                            <p>1234 Gaming Street<br>Tech District, San Francisco<br>CA 94103, USA</p>
                        </div>
                    </div>
                    
                    <div class="info-item">
                        <div class="info-icon">
                            
                        </div>
                        <div class="info-text">
                            <h4>Email Us</h4>
                            <p>support@gaminghub.com<br>sales@gaminghub.com</p>
                        </div>
                    </div>
                    
                    <div class="info-item">
                        <div class="info-icon">
                            
                        </div>
                        <div class="info-text">
                            <h4>Call Us</h4>
                            <p>+1 (555) 123-4567<br>+1 (555) 987-6543</p>
                        </div>
                    </div>
                  </div>
                  </div>
                  </section>
             
</div>
<%@ include file="/WEB-INF/pages/footer.jsp" %>     
</body>
</html>