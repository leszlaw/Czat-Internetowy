import { NgModule } from '@angular/core';
import { Routes, RouterModule , PreloadAllModules} from '@angular/router';
import {HomeComponent} from './modules/home/home.component';
import {ChatComponent} from './modules/chat/chat.component';
import { AuthGuard } from './auth/guards/auth.guard';

const routes: Routes = [
{path: '' , loadChildren: () => import('./modules/home/home.module').then(m => m.HomeModule)},
{path: 'chat' , loadChildren: () => import('./modules/chat/chat.module').then(m => m.ChatModule), canActivate:[AuthGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes,{ preloadingStrategy: PreloadAllModules})],
})
export class AppRoutingModule { }
