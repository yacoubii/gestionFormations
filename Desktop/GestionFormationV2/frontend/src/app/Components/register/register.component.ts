import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../../Sevices/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  form: any = {};
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';
  data: any;

  constructor(private authService: AuthService,private toastr: ToastrService) {}

  ngOnInit() {}

  public delay(ms: number) {
    return new Promise((resolve) => setTimeout(resolve, ms));
  }

  onSubmit() {
    this.authService.register(this.form).subscribe(
      (data) => {
        console.log(data);
        this.data = data;
        this.isSuccessful = true;
        this.isSignUpFailed = false;
        this.authService.link(this.data.code).subscribe(
          (data) => {
            console.log(data);
            this.delay(1000);
          },
          (err) => {
            this.toastr.error(err.message,"Une erreur s'est produite :(");
            this.errorMessage = err.error.message;
            this.isSignUpFailed = true;
          }
        );
        this.toastr.success("Inscription effectuée avec succès!","Félicitations")
      },
      (err) => {
        this.toastr.error(err.message,"Une erreur s'est produite :(");
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
      }
    );
  }
}
