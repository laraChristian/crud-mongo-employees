import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { LoginModel } from './login.model';
import { MessageService } from '../../../../node_modules/primeng/primeng';
import { LoginResponse } from '../../domain/loginResponse';
import { SessionService, WebModel } from '../../services/session.service';
import { Router } from '../../../../node_modules/@angular/router';
import { EmployeeService } from '../../services/employee.service';

@Component({
  selector: 'loggin',
  templateUrl: './login-ui.component.html',
  styleUrls: ['./login-ui.component.css'],
  providers: [EmployeeService],
  encapsulation: ViewEncapsulation.None
})
export class LoginUiComponent implements OnInit {

  private _model: LoginModel;

  constructor(private _loginService: EmployeeService, private messageService: MessageService, private _sessionService: SessionService, private _router: Router) {
    this._model = new LoginModel();

  }

  ngOnInit() {
  }

  login(): void {
    this._loginService.login(this._model.request).subscribe(
      resp => this.processResponse(resp),
      error => console.log('[ERROR] ' + error),
      () => console.log('end -- login method')
    );

  }

  processResponse(response: LoginResponse): void {
    console.log('start -- process-response method with status: ' + response.success);
    if (response.success == true) {
      this._sessionService.save(WebModel.LOGIN, response);
      this._router.navigate(['/users-ui']);
    } else {
      this.messageService.add({ severity: 'error', life: 6000, summary: 'Success', detail: response.message.toString() });
    }
    console.log('end -- process-response method ');
  }

}
