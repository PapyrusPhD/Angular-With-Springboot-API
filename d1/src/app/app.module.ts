import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { BannerComponent } from './components/banner/banner.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { HighlightsComponent } from './components/highlights/highlights.component';
import { CourseComponent } from './components/course/course.component';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { RegisterComponent } from './pages/register/register.component';
import { CoursesComponent } from './pages/courses/courses.component';
import { AboutUsComponent } from './components/about-us/about-us.component';
import { AddCourseComponent } from './pages/add-course/add-course.component';

const appRoutes: Routes = [
  { path: '', component: HomeComponent }, //localhost:4200
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent},
  { path: 'courses', component: CoursesComponent},
  { path: 'courses/add', component: AddCourseComponent},
  { path: 'about', component: AboutUsComponent},
  { path: '**', component: NotFoundComponent },
  
]

@NgModule({
  declarations: [
    AppComponent,
    BannerComponent,
    NavbarComponent,
    HighlightsComponent,
    CourseComponent,
    HomeComponent,
    LoginComponent,
    NotFoundComponent,
    RegisterComponent,
    CoursesComponent,
    AboutUsComponent,
    AddCourseComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
