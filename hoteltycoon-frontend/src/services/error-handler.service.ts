import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root',
})
export class ErrorHandlerService {
  constructor(private toastr: ToastrService) { }

  handleHttpError(error: any) {
    if (error.status === 0) {
      this.showError('Connection error. Please check your internet connection.');
    } else {
      if (error.error.message) {
        this.showError(error.error.message);
      } else {
        // Handle other error cases here
        this.showError('An error occurred. Please try again later.');
      }
    }
  }

  showError(message: string) {
    this.toastr.error(message, 'Error', {
      timeOut: 5000, // Display for 5 seconds
      toastClass: 'toast',
      positionClass: 'toast-top-right',
    });
  }
}
