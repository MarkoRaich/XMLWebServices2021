// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  
  itemsPerPage: 10,
  
  /*  ZA DOCKER
  baseUrlUser:          "http://localhost:8080/api/user", 
  baseUrlContent:       "http://localhost:8080/api/content",
  baseUrlNotification:  "http://localhost:8080/api/notification",
  **/

  /* ZA RAZVOJ U LOCALHOSTU **/
  baseUrlUser:          "http://localhost:8081/", 
  baseUrlContent:       "http://localhost:8082/",
  baseUrlNotification:  "http://localhost:8083/",


  login               : "auth/login",
  registerUser        : "auth/register",
  registerAdmin       : "auth/register-admin",
  myInfo              : "user/my-info",
  changeInfo          : "user/change-info",
  getAccountName      : "user/myAccountName"
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
