import { IsBoolean, IsNotEmpty, IsNumber } from "class-validator";

export class TopoDto{
    @IsNotEmpty()
    @IsBoolean()
    isFlash: boolean;

    @IsNotEmpty()
    @IsNumber()
    nbAttempts: number;
}