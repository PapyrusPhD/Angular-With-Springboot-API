import { Component, OnInit } from '@angular/core';
import { UserService } from '@services/user.service';
import { Router } from '@angular/router';
import { User } from '@models/user';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  firstName: string = '';
  lastName: string = '';
  email: string = '';
  password: string = '';
  confirmPassword: string = '';
  user: User = new User();

  constructor(
    private router: Router,
    private userService: UserService,
  ) { }

  ngOnInit(): void {
  }

  onSubmit() {
    this.user.firstName = this.firstName;
    this.user.lastName = this.lastName;
    this.user.email = this.email;
    if(this.password === this.confirmPassword){
      this.user.password = this.password;
      this.userService.register(this.user).subscribe({
        next: this.successRegister.bind(this),
        error: this.errorRegister.bind(this)
      })
    }
    else{
      Swal.fire('Error', 'Your repeated password is not matched', 'warning')
    }
  }

  successRegister(response: Record<string, any>){
    Swal.fire('Register Successful', 'Your account has been created', 'success')
    this.router.navigate(['/login'])
  }

  errorRegister(response: Record<string, any>){
    let data: Record<string, any> = response['error'];
    Swal.fire('Register unsuccessful', data['details'], 'error')
  }
}
