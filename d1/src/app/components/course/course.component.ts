import { Component, Input, OnInit } from '@angular/core';
import { Course } from '@models/course';
import { SessionService } from '@services/session.service';
import { UserService } from '@services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css']
})
export class CourseComponent implements OnInit {

  //@Input is a decorator which allows us to receive data from another component.
  @Input() course!: Course;

  //When the course component is used or called
  //We will check if there is a token in our localStorage
  //We will also get the isAdmin detail from our localStorage via our sessionService.
  isAdmin: boolean = false;
  hasToken: boolean = (localStorage.getItem('token') !== null);

  constructor(
    private sessionService: SessionService,
    private userService: UserService
  ) {
    this.isAdmin = sessionService.getIsAdmin();
  }

  ngOnInit(): void {
    //console.log(this.course);
  }

  enroll(){
    this.userService.enroll(this.course.id!).subscribe({
      next: this.successfulEnrollment.bind(this),
      error: this.failedEnrollment.bind(this)
    })
  }

  successfulEnrollment(response: Record<string,any>){
    Swal.fire('Enrollment Successful','Enjoy the Course','success');
  }

  failedEnrollment(response: Record<string,any>){
    let data: Record<string,any> = response['error'];

    if(data['result'] === 'already_enrolled'){
      Swal.fire('Enrollment Cancelled','You are already enrolled in this course.','error');
    }
  }

}
