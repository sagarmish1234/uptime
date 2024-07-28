import { type ClassValue, clsx } from "clsx"
import { twMerge } from "tailwind-merge"
import { createAvatar } from '@dicebear/core';
import { lorelei } from '@dicebear/collection';

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs))
}


export function avatarSVG(username: string) {

  const avatar = createAvatar(lorelei, {
    seed: username,
    size: 30
  }).toDataUri();
  console.log(avatar)
  return avatar

}