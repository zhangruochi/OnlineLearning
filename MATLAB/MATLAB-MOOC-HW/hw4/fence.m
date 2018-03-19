function [segments,poles] = fence(lng,seg)
segments = ceil(lng/seg);
poles = segments + 1;
end