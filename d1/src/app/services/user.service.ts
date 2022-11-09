import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';
import { environment } from '@environments/environment';

import { User } from '@models/user';
import { Observable } from 'rxjs';
import { SessionService } from './session.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl: string = environment.apiUrl + '/users';
  private httpHeaders: HttpHeaders = new HttpHeaders({
    //create the http headers for our post method requests that need a token.
    //We need to pass an Authorization header in our token.
    //We add the word 'Bearer ' because our API uses the Bearer token Authentication scheme for our JWT.
    'Authorization': 'Bearer ' + this.sessionService.getToken()
  })

  constructor(
      private http: HttpClient,
      private sessionService: SessionService
  ) { }

  login(email: string, password: string): Observable<Object> { 
      return this.http.post(this.baseUrl + '/login', {email, password});
  }

  register(user: User): Observable<Object>{
    return this.http.post(this.baseUrl + "/register", user)
   }

  enroll(courseId: number): Observable<Object> {
    return this.http.post(this.baseUrl + '/enroll',{courseId},{headers: this.httpHeaders})
  }

}

