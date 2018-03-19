function [first,second,third] = sort3(elements_vector)
        
    if elements_vector(1) >= elements_vector(2)
        if elements_vector(1) >= elements_vector(3)
            third = elements_vector(1);
            if elements_vector(2) > elements_vector(3)
                second = elements_vector(2);
                first = elements_vector(3);
            else
                second = elements_vector(3);
                first = elements_vector(2);
            end
        else
            third = elements_vector(3);
            second = elements_vector(1);
            first = elements_vector(2);
        end
    else
       if elements_vector(2) >= elements_vector(3)
           third = elements_vector(2);
           if elements_vector(1) > elements_vector(3)
               second = elements_vector(1);
               first = elements_vector(3);
           else
               second = elements_vector(3);
               first = elements_vector(1);
           end
           
       else
           third = elements_vector(3);
           second = elements_vector(2);
           first = elements_vector(1);
       end
     end
       
     % sorted_elements = [first,second,third];      
          
end

