import { IsBoolean, IsNotEmpty, IsNumber, IsString } from "class-validator";

export class TopoDto{
    @IsNotEmpty()
    @IsBoolean()
    isFlash: boolean;

    @IsNotEmpty()
    @IsNumber()
    nbAttempts: number;
    
    @IsNotEmpty()
    @IsString()
    comment: string;
}