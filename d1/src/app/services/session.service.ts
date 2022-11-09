import { EventEmitter, Injectable, Output } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  //@Output - decorator that allows us to expose data/field from this class/service to other components/classes.
  //EventEmitter - this field can be subscribed into its changes. It means that we can use a subscribe() method which will be run any time this field is updated.
  @Output() hasToken: EventEmitter<boolean> = new EventEmitter();

  //On the event of using this service,  we will check the localStorage if there is a token. IF there is a token, we will update hasToken as true using the emit() method. Else, we will update the hasToken as false.
  //emit() method will update EventEmitters' values and run its subscribe methods.
  constructor() {
    if(localStorage.getItem('token') !== null){
      this.hasToken.emit(true);
    } else {
      this.hasToken.emit(false);
    }
  }

  //gets token from localStorage
  //! - not null assertion - simply means that we are telling the compiler not to worry this value will not return null.
  getToken(): string {
    return localStorage.getItem('token')!;
  }

  //gets the email of the user from the localStorage
  getEmail(): string {
    return localStorage.getItem('email')!;
  }

  //gets the isAdmin from the localStorage: Uses an operator to convert the string to boolean
  getIsAdmin(): boolean{
    return localStorage.getItem('isAdmin')! === 'true';
  }

  //sets token into the localStorage
  setToken(value: string): void{
    //Whenever the setToken method is used, the hasToken variable/event emitter will be updated as well as in other components where hasToken is used. All subscribe() method of hasToken will be run.
    this.hasToken.emit(true);
    localStorage.setItem('token',value);
  }

  //sets email into localStorage
  setEmail(value: string): void{
    localStorage.setItem('email',value);
  }

  //sets isAdmin into localStorage
  setIsAdmin(value: string): void{
    localStorage.setItem('isAdmin',value);
  }

  //clear() - will logout our users
  //.clear() - to clear out the contents of our browser's localStorage
  //hasToken here will be updated to false
  clear(): void{
    localStorage.clear();
    this.hasToken.emit(false);
  }

  //Note: localStorage is a container of values/data within our browser. These values are stored like key-value pairs.
  //localStorage.setItem('keyName',value) - this will set a key value pair in the browser's localStorage.
  //localStorage.getItem('keyName') - returns the value of the given key. 

}
