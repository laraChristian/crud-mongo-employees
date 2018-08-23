import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/primeng';
import { SessionService, WebModel } from '../../services/session.service';
import { Router } from '@angular/router';
import { LoginModel } from '../login-ui/login.model';
import { LoginResponse } from '../../domain/loginResponse';

@Component({
  selector: 'base-menu',
  templateUrl: './base-menu.component.html',
  styleUrls: ['./base-menu.component.css'],
})
export class BaseMenuComponent implements OnInit {

  private _items: MenuItem[];

  constructor(private session: SessionService, private _router: Router) { }

  ngOnInit() {
    this._items = [{ label: 'Users', icon: 'fa fa-users', routerLink: 'users-ui' },
    {
      label: 'Regions', icon: 'fa fa-ra', items: [
        { label: 'Regions', icon: 'fa fa-adn', routerLink: 'regions-ui' },
        { label: 'Countries', icon: 'fa fa-shirtsinbulk', routerLink: 'countries-ui' },
        { label: 'Locations', icon: 'fa fa-bookmark', routerLink: 'locations-ui' }
      ]
    },
    {
      label: 'Administratives', icon: 'fa fa-cubes', items: [
        { label: 'Departments', icon: 'fa fa-empire', routerLink: 'departments-ui' }
      ]
    }]
  }

  private showToLoggedUsers(): boolean {
    let lm: LoginResponse = this.session.restoreStatus(WebModel.LOGIN, new LoginResponse());
    return lm.success;
  }

  private logout(): void {
    this.session.logout();
    this._router.navigate(['/loggin']);
  }

}
