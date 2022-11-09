import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { SessionService } from '@services/session.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  //hasToken in the Navbar Component will check the localStorage if it has a token. IF there is a token, the inequality operator will return true. Else, it will return false.
  hasToken: boolean = (localStorage.getItem('token') !== null);
  email: String = localStorage.getItem('email')!;

  constructor(
    private sessionService: SessionService,
    private router: Router
  ) {

    //All hasToken (event emitter from session service) subscribe method will be run when .emit() is used to update the event emitter.
    sessionService.hasToken.subscribe(hasTokenEventEmitter => {
      //Whenever sessionService.setToken() is used, hasToken (event emitter) will be updated to true AND its subscribe method is run.
      // console.log('I am run whenever an emit() method of an EventEmitter is run. hasToken is an EventEmitter.');

      //the value of hasToken (event emitter) will be updated into the hasToken variable of our navbar.
      console.log(hasTokenEventEmitter);
      //So, when we login, we update the hasToken (navbar) to true.
      this.hasToken = hasTokenEventEmitter;

      //Whenever we login or logout, we will get the email of our user from the localStorage and update that to our email variable in the navbar component.
      this.email = this.sessionService.getEmail();
    })

  }

  ngOnInit(): void { }

  logout(): void {
    //console.log('Logout button has been clicked');
    this.sessionService.clear();
    this.router.navigate(['/login']);
  }

}
