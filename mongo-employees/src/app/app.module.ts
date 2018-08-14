import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

//components
import { AppComponent } from './app.component';
import { LoginUiComponent } from './ui-components/login-ui/login-ui.component';
import { UsersUiComponent } from './ui-components/users-ui/users-ui.component';
import { BaseMenuComponent } from './ui-components/base-menu/base-menu.component';

//prime modules
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PanelModule, MessageService } from 'primeng/primeng';
import { MenubarModule } from 'primeng/menubar';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast';
import { TooltipModule } from 'primeng/tooltip';
import {TableModule} from 'primeng/table';

//session
import { LocalStorageModule } from 'angular-2-local-storage';
import { SessionService } from './services/session.service';
import { LogginGuard } from './security/loggin-guard';
import { AuthGuard } from './security/auth-guard';
import { routing } from './routing-conf/app-routes';
import { HttpModule } from '@angular/http';
import { CommonModule } from '@angular/common';
import { RegionsUiComponent } from './ui-components/regions-ui/regions-ui.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginUiComponent,
    UsersUiComponent,
    BaseMenuComponent,
    RegionsUiComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    CommonModule,
    ReactiveFormsModule,
    PanelModule,
    routing,
    HttpModule,
    LocalStorageModule.withConfig({
      prefix: 'my-app',
      storageType: 'localStorage'
    }),
    MenubarModule,
    InputTextModule,
    PasswordModule,
    ButtonModule,
    ToastModule,
    TooltipModule,
    TableModule
  ],
  providers: [SessionService, LogginGuard, AuthGuard, MessageService],
  bootstrap: [AppComponent]
})
export class AppModule { }
