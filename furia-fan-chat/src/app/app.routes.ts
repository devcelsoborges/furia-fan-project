import { provideRouter, Routes } from '@angular/router';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { ChatRoomComponent } from './chat-room/chat-room.component';
import { ApplicationConfig, importProvidersFrom } from '@angular/core';
import { provideClientHydration } from '@angular/platform-browser';
import { HttpClientModule, provideHttpClient } from '@angular/common/http';
import { ForumComponent } from './forum/forum.component';
import { TopicDetailComponent } from './topic-detail/topic-detail.component';

export const routes: Routes = [
  { path: '', component: LandingPageComponent },
  { path: 'chat', component: ChatRoomComponent },
  { path: 'forum', component: ForumComponent },
  { path: 'forum/:id', component: TopicDetailComponent },
];

export const routeProviders = provideRouter(routes)

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideClientHydration(),
    importProvidersFrom(HttpClientModule),
    provideHttpClient()
  ],
};