  /**
     * will check if phone state permission is granted or not
     */
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!checkIfHavePhoneStatePermission()) {
                requestForPhoneStatePermission();
            } else {
             // continue your stuff
            }
        } else {
             // continue your stuff
        }
    }   //end of checkPermission

  /**
     * will request for phone state permission
     */
    private void requestForPhoneStatePermission() {
        requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_PHONE_STATE);
    }   //end of requestForPhoneStatePermission




  @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            imeiNumber = getIMEINumber();
            //continue .... 
        } else {
            showToast("Please allow us to request for phone state permission so that we can serve you batter");
            imeiNumber = "0";

        }

    }
