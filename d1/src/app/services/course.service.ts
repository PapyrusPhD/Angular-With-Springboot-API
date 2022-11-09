import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '@environments/environment';
import { Observable, Observer } from 'rxjs';
import { SessionService } from './session.service';
import { HttpHeaders } from '@angular/common/http';

import { Course } from '@models/course';

@Injectable({
  providedIn: 'root'
})
export class CourseService {

  private httpHeaders: HttpHeaders = new HttpHeaders({
    'Authorization': 'Bearer ' + this.sessionService.getToken()
  })
  private baseUrl: string = environment.apiUrl + '/courses';

  constructor(
    private http: HttpClient,
    private sessionService: SessionService,
  ) { }

  //get() method to retrieve our Courses as an array:
  get(): Observable<Course[]>{
    return this.http.get<Course[]>(this.baseUrl);
  }

  add(name: string, description: string, price: string): Observable<Object>{
    return this.http.post(this.baseUrl, {name, description, price}, {headers: this.httpHeaders});
  }
}
