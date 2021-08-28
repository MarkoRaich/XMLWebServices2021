//Odvojen Modul za Angular Material 
//Da ne bi opteretio kod u app.module.ts

import { NgModule } from '@angular/core';

import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatMenuModule } from '@angular/material/menu';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatBadgeModule } from '@angular/material/badge';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { MatRadioModule } from '@angular/material/radio';
import {MatListModule} from '@angular/material/list';
import {MatCardModule} from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatInputModule } from '@angular/material/input';
import { MatDialogModule } from '@angular/material/dialog';
import { MatSelectModule } from '@angular/material/select';
import { MatStepperModule } from '@angular/material/stepper';
import {MatTabsModule} from '@angular/material/tabs';
import { MatAutocompleteModule } from '@angular/material/autocomplete'; 
import { MatSort, MatSortModule } from '@angular/material/sort';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';





@NgModule( {
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        MatMenuModule,
        MatSidenavModule,
        MatBadgeModule,
        MatToolbarModule,
        MatListModule,
        MatCardModule,
        MatFormFieldModule,
        MatDialogModule,
        MatProgressSpinnerModule,
        MatSlideToggleModule,
        MatTableModule,
        MatSortModule,
        MatFormFieldModule,
        MatInputModule,
        MatAutocompleteModule,
        MatTabsModule,
        MatRadioModule
    ],
    exports: [
        MatButtonModule,
        MatToolbarModule,
        MatIconModule,
        MatSidenavModule,
        MatTableModule,
        MatListModule,
        MatCardModule,
        MatFormFieldModule,
        MatProgressSpinnerModule,
        MatPaginatorModule,
        MatSlideToggleModule,
        MatInputModule,
        MatDialogModule,
        MatStepperModule,
        MatSelectModule,
        MatSortModule,
        MatDatepickerModule,
        MatSort,
        MatAutocompleteModule,
        MatTabsModule,
        MatRadioModule
    ],
    providers: [
        MatDatepickerModule,
    ]
} )

export class AngularMaterialModule { }