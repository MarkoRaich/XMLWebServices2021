import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminProfileComponent } from './components/admin-profile/admin-profile.component';
import { LoginComponent } from './components/login/login.component';
import { NonAuthenticatedComponent } from './components/non-authenticated/non-authenticated.component';
import { NonAuthorizedComponent } from './components/non-authorized/non-authorized.component';
import { RegisterUserComponent } from './components/register-user/register-user.component';
import { RegisterComponent } from './components/register/register.component';
import { AdminGuard } from './guards/admin.guard';
import { UserGuard } from './guards/user.guard';
import { SettingsUserComponent } from './components/settings-user/settings-user.component';
import { SearchResultsComponent } from './components/search-results/search-results.component';
import { VerificationRequestsComponent } from './components/verification-requests/verification-requests.component';
import { RegisterAdminComponent } from './components/register-admin/register-admin.component';
import { ProfileComponent } from './components/profile/profile.component';
import { VerifyAccountComponent } from './components/verify-account/verify-account.component';
import { CreatePostComponent } from './components/create-post/create-post.component';
import { CreateStoryComponent } from './components/create-story/create-story.component';
import { CreateAlbumComponent } from './components/create-album/create-album.component';
import { NotificationsComponent } from './components/notifications/notifications.component';

const routes: Routes = [

  
  {
    path: '',
    component: LoginComponent,
    pathMatch: 'full'
  },

  {
    path: 'login',
    component: LoginComponent,
    pathMatch: 'full'
  },

  {
    path: 'register',
    component: RegisterUserComponent,
    pathMatch: 'full'
  },

  {
    path: 'search-results',
    component: SearchResultsComponent,
    pathMatch: 'full'
  },

  {
    path: 'profile/:username',
    component: ProfileComponent,
    pathMatch: 'full'
  },

  {
    path: 'post',
    component: SearchResultsComponent,
    pathMatch: 'full'
  },

  {
    path: 'register/admin',
    component: RegisterComponent,
    pathMatch: 'full',
    canActivate: [AdminGuard]
  },

  {
    path: 'admin-profile',
    component: AdminProfileComponent,
    pathMatch: 'full',
    canActivate: [AdminGuard]
  },

  {
    path: 'verification-requests',
    component: VerificationRequestsComponent,
    pathMatch: 'full',
    canActivate: [AdminGuard]
  },

  {
    path: 'register-admin',
    component: RegisterAdminComponent,
    pathMatch: 'full',
    canActivate: [AdminGuard]
  },

   {
    path: 'settings/user',
    component: SettingsUserComponent,
    pathMatch: 'full',
    canActivate: [UserGuard]
  },

  {
    path: 'verify-profile',
    component: VerifyAccountComponent,
    pathMatch: 'full',
    canActivate: [UserGuard]
  },

  {
    path: 'create-post',
    component: CreatePostComponent,
    pathMatch: 'full',
    canActivate: [UserGuard]
  },

  {
    path: 'create-story',
    component: CreateStoryComponent,
    pathMatch: 'full',
    canActivate: [UserGuard]
  },

  {
    path: 'create-album',
    component: CreateAlbumComponent,
    pathMatch: 'full',
    canActivate: [UserGuard]
  },

  {
    path: 'notifications',
    component: NotificationsComponent,
    pathMatch: 'full',
    canActivate: [UserGuard]
  },




 

  //***************** GRESKE i PRAVA PRISTUPA******************************

  {
    path: 'error/non-authenticated',
    component: NonAuthenticatedComponent,
  },
  {
    path: 'error/non-authorized',
    component: NonAuthorizedComponent
  },


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
