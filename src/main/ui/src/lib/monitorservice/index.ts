import { createClient } from '@/lib/httpclient';

export const pauseUnpauseMonitor = async (
  id: string,
  toPause: boolean
) => {
  console.log({ id, toPause });
  const client = createClient();
  return await client.put(`/monitor/${id}/pause/${toPause}`);
};

export const deleteMonitor = async (id: string) => {
  const client = createClient();
  return await client.delete(`/monitor/${id}/delete`);
};
