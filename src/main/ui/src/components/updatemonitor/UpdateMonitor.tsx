import { MonitorType } from '@/components/monitors/Monitors';
import {
  Sheet,
  SheetContent,
  SheetDescription,
  SheetHeader,
  SheetTitle,
} from '@/components/ui/sheet';
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select';
import { Formik } from 'formik';
import { Input } from '../ui/input';
import { Label } from '../ui/label';
import { Button } from '../ui/button';
import { useQueryClient } from '@tanstack/react-query';
import { createClient } from '@/lib/httpclient';
import { toast } from 'sonner';
import axios from 'axios';

const UpdateMonitor = ({
  monitor,
  openSheet,
  controlOpen,
}: {
  monitor: MonitorType;
  openSheet: boolean;
  controlOpen: React.Dispatch<React.SetStateAction<boolean>>;
}) => {
  const queryClient = useQueryClient();
  const client = createClient();
  return (
    <div>
      <Sheet
        defaultOpen={openSheet}
        open={openSheet}
        onOpenChange={controlOpen}
      >
        {/* <SheetTrigger asChild></SheetTrigger> */}
        <SheetContent>
          <SheetHeader>
            <SheetTitle className="text-2xl">
              Update monitor
            </SheetTitle>
            <SheetDescription>
              Update the target monitor
            </SheetDescription>
          </SheetHeader>
          <Formik
            initialValues={{
              url: monitor.url,
              checkFrequency: monitor.checkFrequency,
            }}
            onSubmit={async (values) => {
              console.log(values);
              const id = toast.loading('In progress');
              try {
                await client.put(
                  `/monitor/${monitor.id}/update`,
                  values
                );
                toast.success('Monitor updated successfully', {
                  id,
                  duration: 2000,
                });
              } catch (e) {
                console.log(e);
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
              controlOpen(false);
            }}
          >
            {({
              values,
              handleChange,
              handleBlur,
              handleSubmit,
              setFieldValue,
              isSubmitting,
            }) => (
              <form
                onSubmit={handleSubmit}
                className="mt-10 w-2/3 flex flex-col gap-6"
              >
                <div>
                  <Label htmlFor="url">URL to monitor</Label>
                  <Input
                    name="url"
                    id="url"
                    onChange={handleChange}
                    onBlur={handleBlur}
                    value={values.url}
                    placeholder=""
                  />
                </div>
                <div>
                  <Label htmlFor="check-frequency">
                    Check frequency
                  </Label>
                  <Select
                    value={values.checkFrequency}
                    onValueChange={(value) =>
                      setFieldValue('checkFrequency', value)
                    }
                  >
                    <SelectTrigger>
                      <SelectValue id="check-frequency" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="EVERY_3_MINUTES">
                        3 minutes
                      </SelectItem>
                      <SelectItem value="EVERY_5_MINUTES">
                        5 minutes
                      </SelectItem>
                      <SelectItem value="EVERY_15_MINUTES">
                        15 minutes
                      </SelectItem>
                      <SelectItem value="EVERY_30_MINUTES">
                        30 minutes
                      </SelectItem>
                      <SelectItem value="EVERY_60_MINUTES">
                        60 minutes
                      </SelectItem>
                    </SelectContent>
                  </Select>
                </div>
                <Button type="submit" disabled={isSubmitting}>
                  Update monitor
                </Button>
              </form>
            )}
          </Formik>
        </SheetContent>
      </Sheet>
    </div>
  );
};

export default UpdateMonitor;
