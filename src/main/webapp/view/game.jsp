<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<html>
<head>
    <meta charset="UTF-8">
    <title>Dungeon of mistra</title>
    <link rel="stylesheet" href="../static/css/game.css">
</head>
<body>
    <audio src="${currentQuestion.audioPath}" autoplay="autoplay" ></audio>
    <c:set var="idBoss" value="${currentQuestion.id}"/>

    <div class="game-page-img">

        <div class="progress-container">
            <div class="progress-bar" id="progress-bar" style="width: ${currentProgressPercentage}%"></div>
        </div>

        <div class="text-about-progress"> Крок ${currentQuestion.id} із 10 </div>

        <c:choose>
            <c:when test="${idBoss >= 6 and idBoss < 8}">
                <div class="boss-img">
                    <img src="../images/Boss.webp">
                </div>
            </c:when>
            <c:when test="${idBoss >= 8 and idBoss < 9}">
                <div class="boss-img-dead">
                    <img src="../images/Boss_Dead.webp">
                </div>
            </c:when>
            <c:when test="${idBoss >= 9 and idBoss < 10}">
                <div class="altar-img">
                    <img src="../images/altar.webp">
                </div>
            </c:when>
            <c:when test="${idBoss == 10}">
                <div class="scroll-img">
                    <img src="../images/Th_pile_of_scrolls.webp">
                </div>
            </c:when>
        </c:choose>

        <div class="container">
            <div class="block">
                    <c:choose>
                        <c:when test="${idBoss ge 6 and idBoss lt 8}">
                            <div class="game-page-fight">
                                <img src="../images/Hwm_combat.webp">
                                <img src="../images/Crusader_combat.webp">
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="game-page-left">
                                <img src="../images/Hwm_walk.webp">
                                <img src="../images/Crusader_walk.webp">
                            </div>
                        </c:otherwise>
                    </c:choose>
            </div>
            <div class="block">
                <h1 class="text-about">${currentQuestion.questText}</h1>
            </div>
        </div>
        <c:forEach var="answer" items="${currentQuestion.answers}">
            <form action="/game" method="post">
                <button class="button-85" role="button" type="submit" name="userChoice" value="${answer}">
                    <span>${answer}</span>
                </button>
            </form>
        </c:forEach>
    </div>
</body>
</html>
