
<header>
    <h1>Welcome, Teacher</h1>
</header>
<nav>
    <ul>
        <li><a href="mycourses.html">My Courses</a></li>
        <li><a href="createcourse.html">Create Course</a></li>
        <li><a href="studentmanagement.html">Student Management</a></li>
        <li><a href="messages.html">Messages</a></li>
        <li><a href="logout.html">Logout</a></li>
    </ul>
</nav>
<main>
    <section>
        <h2>My Courses</h2>
        <ul class="course-list">
            <li>
                <h3>Course Title 1</h3>
                <a href="course1.html">Edit Course</a>
            </li>
            <li>
                <h3>Course Title 2</h3>
                <a href="course2.html">Edit Course</a>
            </li>
            <!-- Add more course listings here -->
        </ul>
    </section>
    <section>
        <h2>Create a New Course</h2>
        <a href="createcourse.html">Create a New Course</a>
    </section>
    <section>
        <h2>Student Management</h2>
        <ul class="student-list">
            <li>
                <h3>[Student Name]</h3>
                <p>Progress: 60%</p>
                <a href="studentdetails.html">View Details</a>
            </li>
            <li>
                <h3>[Student Name]</h3>
                <p>Progress: 40%</p>
                <a href="studentdetails.html">View Details</a>
            </li>
            <!-- Add more student listings here -->
        </ul>
    </section>
    <section>
        <h2>Messages</h2>
        <ul class="message-list">
            <li>
                <h3>[Student Name]</h3>
                <p>Message content...</p>
            </li>
            <li>
                <h3>[Colleague's Name]</h3>
                <p>Message content...</p>
            </li>
            <!-- Add more messages here -->
        </ul>
    </section>
</main>
<footer>
    <p>&copy; 2023 Online Learning Management System</p>
</footer>


<style>
    body, h1, h2, ul, li {
        margin: 0;
        padding: 0;
    }

    body {
        font-family: Arial, sans-serif;
        background-color: #f0f0f0;
        margin: 0;
        padding: 0;
    }

    header {
        background-color: #007acc;
        color: white;
        text-align: center;
        padding: 20px;
    }

    nav {
        background-color: #333;
        color: white;
        padding: 10px;
    }

    nav ul {
        list-style: none;
        text-align: center;
    }

    nav li {
        display: inline;
        margin-right: 20px;
    }

    nav a {
        text-decoration: none;
        color: white;
    }

    main {
        max-width: 1200px;
        margin: 0 auto;
        padding: 20px;
    }

    section {
        margin-bottom: 20px;
        background-color: #fff;
        padding: 20px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }

    footer {
        background-color: #333;
        color: white;
        text-align: center;
        padding: 10px;
    }

</style>

