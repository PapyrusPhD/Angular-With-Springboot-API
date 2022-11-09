import { Component, OnInit } from '@angular/core';
import { Course } from '@models/course';
import { CourseService } from '@services/course.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-course',
  templateUrl: './add-course.component.html',
  styleUrls: ['./add-course.component.css']
})
export class AddCourseComponent implements OnInit {

  name: string = '';
  description: string = '';
  price: string = '';
  
  course: Course = new Course();

  constructor(
    private courseService: CourseService,
    private router: Router,
  ) { }

  ngOnInit(): void {
  }

  onSubmit(){
    this.course.name = this.name;
    this.course.description = this.description;
    this.course.price = this.price;
    this.courseService.add(this.name, this.description, this.price).subscribe({
      next: this.addSuccess.bind(this),
      error: this.addError.bind(this)
    });
  }

  addSuccess(response: Record<string, any>){
    Swal.fire('Added successfully', 'Course has been created', 'success')
    this.router.navigate(['/courses'])
  }

  addError(response: Record<string, any>){
    Swal.fire('Added unsuccessfully', 'Course has not been added', 'error')
  }
}
