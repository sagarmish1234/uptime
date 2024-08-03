import {
  Settings,
  Pause,
  Trash2,
  Play,
  MoreHorizontal,
} from 'lucide-react';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
  DropdownMenuItem,
} from '@/components/ui/dropdown-menu';
import { Button } from '@/components/ui/button';
import {
  pauseUnpauseMonitor,
  deleteMonitor,
} from '@/lib/monitorservice';
import { useQueryClient } from '@tanstack/react-query';
import { MonitorType } from '@/components/monitors/Monitors';
import { toast } from 'sonner';
import axios from 'axios';

const MonitorActions = ({ monitor }: { monitor: MonitorType }) => {
  const queryClient = useQueryClient();
  return (
    <DropdownMenu>
      <DropdownMenuTrigger asChild>
        <Button variant="ghost" className="h-8 w-8 p-0">
          <span className="sr-only">Open menu</span>
          <MoreHorizontal className="h-4 w-4" />
        </Button>
      </DropdownMenuTrigger>
      <DropdownMenuContent align="end">
        <DropdownMenuLabel className="text-muted-foreground/30">
          Actions
        </DropdownMenuLabel>
        {monitor &&
          (monitor.currentStatus != 'PAUSED' ? (
            <DropdownMenuItem
              onClick={async () => {
                const id = toast.loading('In progress');
                try {
                  await pauseUnpauseMonitor(monitor.id, true);
                  toast.success('Monitor paused successfully', {
                    id,
                    duration: 2000,
                  });
                } catch (e) {
                  if (axios.isAxiosError(e)) {
                    toast.error(e?.response?.data?.message, {
                      id,
                      duration: 2000,
                    });
                  }
                }
                queryClient.invalidateQueries({
                  queryKey: ['monitors'],
                });
              }}
            >
              <Pause className="mr-2 h-4 w-4" />
              <span>Pause</span>
            </DropdownMenuItem>
          ) : (
            <DropdownMenuItem
              onClick={async () => {
                const id = toast.loading('In progress');
                try {
                  await pauseUnpauseMonitor(monitor.id, false);
                  queryClient.invalidateQueries({
                    queryKey: ['monitors'],
                  });
                  toast.success('Monitor unpaused successfully', {
                    id,
                    duration: 2000,
                  });
                } catch (e) {
                  if (axios.isAxiosError(e)) {
                    toast.error(e?.response?.data?.message, {
                      id,
                      duration: 2000,
                    });
                  }
                }
              }}
            >
              <Play className="mr-2 h-4 w-4" />
              <span>Unpause</span>
            </DropdownMenuItem>
          ))}
        <DropdownMenuItem>
          <Settings className="mr-2 h-4 w-4" />
          <span>Configure</span>
        </DropdownMenuItem>
        <DropdownMenuSeparator />
        <DropdownMenuItem
          onClick={async () => {
            const id = toast.loading('In progress');
            try {
              await deleteMonitor(monitor.id);
              queryClient.invalidateQueries({
                queryKey: ['monitors'],
              });
              toast.success('Monitor removed successfully', {
                id,
                duration: 2000,
              });
            } catch (e) {
              if (axios.isAxiosError(e)) {
                toast.error(e?.response?.data?.message, {
                  id,
                  duration: 2000,
                });
              }
            }
          }}
        >
          <Trash2 className="mr-2 h-4 w-4" />
          <span>Remove</span>
        </DropdownMenuItem>
      </DropdownMenuContent>
    </DropdownMenu>
  );
};

export default MonitorActions;
