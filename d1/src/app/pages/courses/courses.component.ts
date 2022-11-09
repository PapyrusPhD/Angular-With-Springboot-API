import { Component, OnInit } from '@angular/core';

import { Course } from '@models/course';
import { CourseService } from '@services/course.service';
import { UserService } from '@services/user.service';
import { SessionService } from '@services/session.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.css']
})
export class CoursesComponent implements OnInit {

  courses: Course[] = [];
  isAdmin: boolean = false;
  hasToken: boolean = (localStorage.getItem('token') !== null);

  constructor(
    private courseService: CourseService,
    private userService: UserService,
    private sessionService: SessionService,
    private router: Router,
  ) {
    //run the getCourses method in the constructor so that when the courses page is accessed and used, we will run the getCourses() method.
    this.getCourses();
    this.isAdmin = sessionService.getIsAdmin();
  }

  ngOnInit(): void {
    
  }

  getCourses(){
    this.courseService.get().subscribe((response: Course[])=>{
      //console.log(response);
      
      //courses field in Courses page should now contain the array of courses from our DB.
      this.courses = response;
    })
  }

  addCourses(){
    this.router.navigate(['courses/add']);
  }
}
