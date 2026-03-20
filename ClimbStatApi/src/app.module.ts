import { Module } from '@nestjs/common';
import { ConfigModule } from '@nestjs/config';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { MongooseModule } from '@nestjs/mongoose';
import { AuthModule } from './auth/auth.module';
import { UsersModule } from './users/users.module';
import { ClimbingGym } from './climbingGym/shemas/climbingGym.shema';
import { ClimbingGymModule } from './climbingGym/climbingGym.module';

@Module({
  imports: [
    ConfigModule.forRoot({
      isGlobal: true,
    }),
    MongooseModule.forRoot(process.env.MONGO_URI || 'mongodb://localhost:27017'),
    AuthModule,
    UsersModule,
    ClimbingGymModule
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
