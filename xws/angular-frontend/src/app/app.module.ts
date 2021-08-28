import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AngularMaterialModule } from './angular-material/angular-material.module';
import { ToastrModule } from 'ngx-toastr';
import { AppRoutingModule } from './app-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatNativeDateModule } from '@angular/material/core';
import { NgImageSliderModule } from 'ng-image-slider';

import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { NonAuthenticatedComponent } from './components/non-authenticated/non-authenticated.component';
import { NonAuthorizedComponent } from './components/non-authorized/non-authorized.component';
import { RegisterUserComponent } from './components/register-user/register-user.component';
import { SettingsUserComponent } from './components/settings-user/settings-user.component';
import { AdminProfileComponent } from './components/admin-profile/admin-profile.component';

import { AdminGuard } from './guards/admin.guard';
import { UserGuard } from './guards/user.guard';
import { AuthInterceptor } from './interceptors/auth.interceptor';
import { ErrorInterceptor } from './interceptors/error.interceptor';
import { SearchResultsComponent } from './components/search-results/search-results.component';
import { VerificationRequestsComponent } from './components/verification-requests/verification-requests.component';
import { RegisterAdminComponent } from './components/register-admin/register-admin.component';
import { ProfileComponent } from './components/profile/profile.component';
import { FollowerRequestDialogComponent } from './components/follower-request-dialog/follower-request-dialog.component';
import { FollowersDialogComponent } from './components/followers-dialog/followers-dialog.component';
import { CloseFriendsComponent } from './components/close-friends/close-friends.component';
import { RatingsDialogComponent } from './components/ratings-dialog/ratings-dialog.component';
import { CollectionDialogComponent } from './components/collection-dialog/collection-dialog.component';
import { VerifyAccountComponent } from './components/verify-account/verify-account.component';
import { CreatePostComponent } from './components/create-post/create-post.component';
import { CreateStoryComponent } from './components/create-story/create-story.component';
import { CreateAlbumComponent } from './components/create-album/create-album.component';
import { NotificationsComponent } from './components/notifications/notifications.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    NonAuthenticatedComponent,
    NonAuthorizedComponent,
    RegisterUserComponent,
    SettingsUserComponent,
    AdminProfileComponent,
    SearchResultsComponent,
    VerificationRequestsComponent,
    RegisterAdminComponent,
    ProfileComponent,
    FollowerRequestDialogComponent,
    FollowersDialogComponent,
    CloseFriendsComponent,
    RatingsDialogComponent,
    CollectionDialogComponent,
    VerifyAccountComponent,
    CreatePostComponent,
    CreateStoryComponent,
    CreateAlbumComponent,
    NotificationsComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    NgImageSliderModule,
    MatNativeDateModule,
    AngularMaterialModule,
    ToastrModule.forRoot({
      timeOut: 2000,
      positionClass: 'toast-top-right',
      preventDuplicates: true,
    }),
    BrowserAnimationsModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    AdminGuard,
    UserGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
